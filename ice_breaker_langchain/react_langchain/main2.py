from dotenv import load_dotenv

from langchain.prompts import PromptTemplate
from langchain.chat_models import ChatOpenAI
from langchain.agents import tool, initialize_agent, AgentType
from langchain.agents.output_parsers import ReActSingleInputOutputParser
from langchain.tools.render import render_text_description

load_dotenv()


@tool
def get_text_length(text: str) -> int:
    """Returns the length of a text by caracters"""
    print(f"get_text_length enter with {text=}")
    return len(text)


if __name__ == "__main__":
    print("Hello")

    # llm = ChatOpenAI()
    # agent_executor = initialize_agent(
    #     tools=[get_text_length], 
    #     llm=llm,
    #     agent=AgentType.ZERO_SHOT_REACT_DESCRIPTION,
    #     verbose=True
    # )
    # agent_executor.invoke({"input": "What is the length of the text Dog?"})

    tools = [get_text_length]

    template = """
    Answer the following questions as best you can. You have access to the following tools:\

    {tools}

    Use the following format:

    Question: the input question you must answer
    Thought: you should always think about what to do
    Action: the action to take, should be one of [{tool_names}]
    Action Input: the input to the action
    Observation: the result of the action
    ... (this Thought/Action/Action Input/Observation can repeat N times)
    Thought: I now know the final answer
    Final Answer: the final answer to the original input question

    Begin!

    Question: {input}
    Thought: {agent_scratchpad}
    """

    prompt = PromptTemplate.from_template(template=template).partial(
        tools=render_text_description(tools=tools),
        tool_names=", ".join([t.name for t in tools]),
    )

    llm = ChatOpenAI(
        temperature=0, stop=["\nObservation"], callbacks=[AgentCallbackHandler()]
        #model="gpt-3.5-turbo", model_kwargs={'stop': ["\nObservation"]})
    )
    intermediate_steps = []

    agent = (
        {
            "input": lambda x: x["input"],
            "agent_scratchpad": lambda x: format_log_to_str(x["agent_scratchpad"]),
        } 
        | prompt 
        | llm 
        | ReActSingleInputOutputParser()
    )

    agent_step = ""
    while not isinstance(agent_step, AgentFinish):
        agetn_step: Union[AgentAction, AgentFinish] = agent.invoke(
            {
                "input": "Write a haiku about dogs and then count the length by characters?",
                "agent_scratchpad": intermediate_steps,
            }
        )
        print(agent_step)

        if isinstance(agent_step, AgentAction):
            tool_name = agetn_step.tool
            tool_to_use = find_tool_by_name(tools, tool_name)
            tool_input = agent_step.tool_input

            observation = tool_to_use.func(str(tool_input))
            print(f"{observation=}")
            intermediate_steps.append((agent_step, str(observation)))

    if isinstance(agent_step, AgentFinish):
        print(agent_step.return_values)

    # res = agent.invoke({"input": "What is the length of 'DOG' in characters?"})
    # print(res)
