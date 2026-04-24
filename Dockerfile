FROM eclipse-temurin:17-jdk

# Install Chrome and dependencies
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    gnupg \
    unzip \
    chromium-browser \
    chromium-chromedriver \
    ca-certificates \
    fonts-liberation \
    libappindicator3-1 \
    libatk-bridge2.0-0 \
    libatk1.0-0 \
    libcups2 \
    libdbus-1-3 \
    libexpat1 \
    libgbm1 \
    libgcc1 \
    libgconf-2-4 \
    libgdk-pixbuf1.0-0 \
    libglib2.0-0 \
    libgtk-3-0 \
    libnspr4 \
    libnss3 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    libstdc++6 \
    libx11-6 \
    libx11-xcb1 \
    libxcb1 \
    libxcomposite1 \
    libxcursor1 \
    libxdamage1 \
    libxext6 \
    libxfixes3 \
    libxi6 \
    libxrandr2 \
    libxrender1 \
    libxshmfence1 \
    libxss1 \
    libxtst6 \
    lsb-release \
    xdg-utils \
    && rm -rf /var/lib/apt/lists/*

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Verify Chrome and ChromeDriver installation
RUN chromium-browser --version && which chromedriver

# Create symlinks for common paths if needed
RUN ln -sf /usr/bin/chromium-browser /usr/bin/chromium 2>/dev/null || true

# Create working directory
WORKDIR /app

# Copy project files
COPY . .

# Create test-output directory
RUN mkdir -p test-output

# Set environment variable to indicate Docker environment
ENV DOCKER_CONTAINER=true

# Run tests
CMD ["mvn", "clean", "test"]
