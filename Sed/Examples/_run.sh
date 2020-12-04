
cd MdFiles

CLASSNAME="UpdateAllMdFiles"
scala -classpath ".:../lib/sed_2.12-0.4.jar:../lib/kaleidoscope_2.12-0.1.0.jar:../${CLASSNAME}.jar" ${CLASSNAME}


