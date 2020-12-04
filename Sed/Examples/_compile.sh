
CLASSNAME="UpdateAllMdFiles"
scalac -classpath ".:lib/sed_2.12-0.4.jar:lib/kaleidoscope_2.12-0.1.0.jar" ${CLASSNAME}.scala -d ${CLASSNAME}.jar
echo "CREATED ${CLASSNAME}.jar (hopefully)"

