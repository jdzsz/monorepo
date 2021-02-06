FROM ubuntu:focal

COPY metrics/coverage_converter/coverage_converter_linux /usr/local/bin/coverage_converter
RUN chmod +x /usr/local/bin/coverage_converter
CMD ["coverage_converter"]
