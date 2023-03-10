######  #     #   ###   #       ######
#     # #     #    #    #       #     #
#     # #     #    #    #       #     #
######  #     #    #    #       #     #
#     # #     #    #    #       #     #
#     # #     #    #    #       #     #
######   #####    ###   ####### ######
FROM maven:3.8.4-openjdk-11-slim AS builder

WORKDIR /build

COPY ./pom.xml .

# Copy source code
COPY ./src ./src

COPY settings.xml .

# Build application
RUN mvn -s settings.xml clean deploy