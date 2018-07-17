FROM alpine/git as clone
ARG url
WORKDIR /app
RUN git clone https://github.com/RobYed/kitchen-bob-server

FROM maven:3.2-jdk-8 as build
WORKDIR /app
COPY --from=clone /app/kitchen-bob-server /app
RUN mvn package

FROM openjdk:8-jre-alpine
ARG version
ENV artifact kitchenbob-${version}.jar
WORKDIR /app
COPY --from=build /app/target/${artifact} /app
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar ${artifact}"]