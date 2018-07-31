mvn -f ../pom.xml versions:set -DnewVersion=$1

mvn -f ../pom.xml clean install -DskipTests
