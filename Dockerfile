#import ubuntu 
FROM ubuntu

#install dependecies
RUN apt-get update
RUN apt-get -y install nginx
RUN apt-get -y install openjdk-17-jre

#config nginx
COPY default.conf /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/nginx.conf
COPY parking-system-frontend/dist/parking-system-frontend /usr/share/nginx/html

#get run file
COPY run.sh .
RUN chmod +x run.sh

#run api
ARG JAR_FILE=parkingsystem/target/*.jar
ADD ${JAR_FILE} app.jar
#RUN java -jar app.jar
ENTRYPOINT ["./run.sh"]
