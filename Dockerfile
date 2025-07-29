FROM openjdk:26-jdk
ADD target/telegram-bot-cleanCloud-merge.jar telegram-bot-cleanCloud-merge.jar
ENTRYPOINT ["java","-jar","telegram-bot-cleanCloud-merge.jar"]