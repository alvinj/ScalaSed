
CLASSNAME="AddRedirectFrom"
scalac -classpath ".:sed_2.12-0.2.jar:kaleidoscope_2.12-0.1.0.jar" ${CLASSNAME}.scala -d ${CLASSNAME}.jar
echo "CREATED ${CLASSNAME}.jar (hopefully)"

