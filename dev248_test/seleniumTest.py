from selenium import webdriver

from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
import time
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
import time 
import os
import re

def error(msg):
    print(msg)
    exit(1)


chrome_options = Options()
chrome_options.add_argument("--start-maximized");

## in case of Linux execution

chrome_options.add_argument("--window-size=1920,1080");
chrome_options.add_argument("--disable-gpu");
chrome_options.add_argument("--disable-extensions");
chrome_options.add_argument("--proxy-server='direct://'");
chrome_options.add_argument("--proxy-bypass-list=*");
chrome_options.add_argument("--headless")
chrome_options.add_argument("--no-sandbox")
#chrome_options.setExperimentalOption("useAutomationExtension", False);

driver = webdriver.Chrome( options=chrome_options) 
  

## edison login 
try :
    driver.get("https://www.edison.re.kr/home?p_p_id=58&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&saveLastPath=false&_58_struts_action=%2Flogin%2Flogin")
    driver.find_element(By.ID,'_58_login').send_keys("siahn93")
    driver.find_element(By.ID,'_58_password').send_keys("******")
    driver.find_element(By.CSS_SELECTOR, "button.btn.login-btn.btn-primary").click()
except :
    error("error - login")    
time.sleep(5)


## move to GAMESS solver
try :
    driver.get("https://www.edison.re.kr/workbench?p_p_id=SimulationWorkbench_WAR_OSPWorkbenchportlet&p_p_lifecycle=0&p_p_state=pop_up&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_SimulationWorkbench_WAR_OSPWorkbenchportlet_workbenchType=SIMULATION_WITH_APP&_SimulationWorkbench_WAR_OSPWorkbenchportlet_scienceAppId=21301&_SimulationWorkbench_WAR_OSPWorkbenchportlet_simulationProjectId=0")
except :
    error("error - move to GAMESS solver")     
time.sleep(15)


## click new button
try :
    driver.find_element(By.CSS_SELECTOR, "i.fa.fa-plus-square-o.fa-2x").click()
except :
    error("error - job submit : new button") 
time.sleep(2)


## click create button
try :
    driver.find_element(By.CSS_SELECTOR, "button.btn.btn-primary.btn-flat.pull-right.func").click()
except :
    error("error - job submit : create button")
time.sleep(2)

    ## click menu button 
    ## there are multiple menus, so that find_elements are used. 
try :
    menu = driver.find_elements(By.CSS_SELECTOR, "button.btn.btn-primary.dropdown-toggle") 
    menu[0].click()
except :
    error("error - job submit : menu button")
time.sleep(2)

## click sample button
try : 
    driver.find_element(By.ID, '_TextEditor_WAR_OSPTextEditorportlet_INSTANCE_LAYOUT_sample').click()
except :
    error("error - job submit : sample button")
    time.sleep(2)

## click submit button
try :
    driver.find_element(By.CSS_SELECTOR, "i.fa.fa-cloud-upload.fa-2x").click()
except :
    error("error - job submit : submit button")  
time.sleep(30)


## check error

if driver.find_element(By.ID, '_OSPJSMol_WAR_OSPJSMolportlet_INSTANCE_LAYOUT_title').text != 'gamess_output.log' : 
    error("error - no title in Molportlet")
elif driver.find_element(By.ID, '_OSPTextViewer_WAR_OSPTextViewerportlet_INSTANCE_LAYOUT_title').text != 'gamess_output.log' : 
    error("error - no title in TextViewerportlet")
elif len(driver.find_element(By.ID, '_OSPTextViewer_WAR_OSPTextViewerportlet_INSTANCE_LAYOUT_canvas').text) < 40000 :
    error("error - not sufficient results in TextViewerportlet")


print("Success")
driver.get_screenshot_as_file("capture.png")

driver.close()
