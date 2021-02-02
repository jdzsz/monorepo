FROM google/dart:2.12-beta

RUN DEBIAN_FRONTEND=noninteractive \
    && apt-get update && apt-get install -y wget \
    && wget https://github.com/platform-platform/monorepo/releases/download/coverage-converter-snapshot/coverage_converter_linux -O /usr/local/bin/coverage_converter \
    && chmod a+x /usr/local/bin/coverage_converter \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*
