# Jenkins Setup Instructions

## 1. Configure Jenkins Tools

Go to **Manage Jenkins → Configure System → Global Tool Configuration**

### JDK Configuration
- Click "Add JDK"
- Name: `JDK17`
- JAVA_HOME: `/usr/lib/jvm/java-17-openjdk` (or your Java 17 path)
- OR check "Install automatically" and select Java 17

### Maven Configuration
- Click "Add Maven"
- Name: `Maven3`
- Version: Select `3.9.4` (or latest 3.x)
- OR check "Install automatically"

## 2. Install Chrome on Jenkins Agent

### For Linux Agent (Ubuntu/Debian):
```bash
sudo apt-get update
sudo apt-get install -y wget gnupg
wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
sudo apt-get update
sudo apt-get install -y google-chrome-stable
google-chrome --version
```

### For Linux Agent (CentOS/RHEL):
```bash
sudo yum install -y wget
wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm
sudo yum localinstall -y google-chrome-stable_current_x86_64.rpm
google-chrome --version
```

### For Windows Agent:
- Download Chrome from: https://www.google.com/chrome/
- Install it in `C:\Program Files\Google\Chrome`
- WebDriverManager will automatically find it

## 3. Create Jenkins Job

### Via Jenkins UI:
1. Click "New Item"
2. Name: `Selenium-Tests`
3. Type: **Pipeline**
4. In Pipeline section:
   - Definition: **Pipeline script from SCM**
   - SCM: **Git**
   - Repository URL: `https://github.com/your-username/Selenium-setup.git`
   - Branch: `*/main` or `*/master`
   - Script Path: `Jenkinsfile`
5. Click **Save**

### Build the Job:
- Click **Build Now**
- Check build logs for Chrome installation and test execution
- After build, ExtentReport will be archived as artifact

## 4. Verify Setup

In Jenkins build logs, you should see:
```
✓ JDK17 initialized
✓ Maven3 initialized
✓ Repository checked out
✓ Chrome installed (version X.X.X)
✓ Tests executed
✓ Report generated
```

## 5. View Reports

After build completes:
1. Go to build page
2. Click **Artifacts**
3. Download `ExtentReport.html`
4. Open in browser

## 6. Optional: Set up Webhooks for Auto-Build

In GitHub → Settings → Webhooks:
- Payload URL: `https://your-jenkins/github-webhook/`
- Content type: `application/json`
- Events: Push events
- Now builds trigger automatically on push!
