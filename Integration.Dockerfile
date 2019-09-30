FROM gradle:5.6.2-jre8
RUN mkdir -p /root/build

WORKDIR /root/build

COPY ./ /root/build

CMD ./gradlew run integration
