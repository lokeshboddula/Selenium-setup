# Install Chrome on Jenkins Agent
# Run these commands on your Jenkins agent/server:

# For Ubuntu/Debian:
sudo apt-get update
sudo apt-get install -y wget gnupg
wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
sudo apt-get update
sudo apt-get install -y google-chrome-stable

# Verify installation:
google-chrome --version

# For CentOS/RHEL:
sudo yum install -y wget
wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm
sudo yum localinstall -y google-chrome-stable_current_x86_64.rpm

# Verify installation:
google-chrome --version

# For Windows Jenkins agents:
# Download and install Chrome from: https://www.google.com/chrome/
# ChromeDriver will be managed by WebDriverManager
