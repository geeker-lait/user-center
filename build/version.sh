mvn -f ~/build/user-center/pom.xml versions:set -DnewVersion=$1

mvn -f ~/build/user-center/pom.xml clean install -DskipTests
