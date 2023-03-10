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
RUN mvn -s settings.xml package

######  #     # #     #
#     # #     # ##    #
#     # #     # # #   #
######  #     # #  #  #
#   #   #     # #   # #
#    #  #     # #    ##
#     #  #####  #     #
FROM openjdk:11-jre-slim

WORKDIR /

# Add application built jar file
COPY --from=builder /build/target/*.jar app.jar

ENV JAVA_OPTS=""

CMD java $JAVA_OPTS -jar app.jar