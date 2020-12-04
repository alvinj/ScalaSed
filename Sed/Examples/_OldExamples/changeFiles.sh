for file in `ls *md`
do
  #sed -i.bak -f my_commands.sed $file
  sed -i.bak -e's/layout: multipage-overview/layout: tour/' $file
done


