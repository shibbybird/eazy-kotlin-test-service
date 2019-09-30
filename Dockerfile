FROM java:8u222-jre

RUN useradd -ms /bin/bash eazyci

USER eazyci

WORKDIR /home/eazyci

COPY build/libs/eazy-kotlin-test-service.jar /home/eazyci

CMD java -jar eazy-kotlin-test-service.jar
