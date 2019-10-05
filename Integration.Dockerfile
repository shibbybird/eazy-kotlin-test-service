FROM gradle:5.2.1-jdk8

USER root

RUN mkdir -p /root/build

RUN apt-get update
RUN apt-get -y install python-pip
RUN pip install cqlsh

WORKDIR /root/build

COPY ./ /root/build

CMD ./gradlew integration
