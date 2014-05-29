mvn package -Dmaven.javadoc.skip=true \
	    -DskipTests=true && \
java  \
  -DBASE_URL="http://192.168.50.1:9085/" \
  -jar ~/Downloads/jetty-runner-9.1.0.RC2.jar \
  --path "" \
  --port 9085 \
  openid-connect-server-webapp/target/openid-connect-server-webapp.war
