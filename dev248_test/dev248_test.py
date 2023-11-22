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

TEST_URL = "https://dev248.edison.re.kr:8443/"
KEYCLOAK_AUTH = "https://dev248.edison.re.kr:8543/auth/"

#------------------------------------
# LOGIN
#------------------------------------
try:
    driver.get("https://dev248.edison.re.kr:8543/auth/realms/EDISON2/protocol/openid-connect/auth?scope=openid+profile+email&response_type=code&redirect_uri=https%3A%2F%2Fdev248.edison.re.kr%3A8443%2Fc%2Fportal%2Flogin%2Fopenidconnect&state=HwlyEAgZZIdZrTbJgJQsc-F-h0VTjrSIdiLIFG4qkMQ&nonce=dRdGrii3dihedqkkSSQxTMqE4zHPfatrN16lDnXVkYc&client_id=liferay-portal-client")
    driver.find_element(By.NAME, 'username').send_keys("demo")
    driver.find_element(By.NAME, "password").send_keys("demo")
    driver.find_element(By.ID, "kc-login")
except:
    error("error - login")
time.sleep(10)


    