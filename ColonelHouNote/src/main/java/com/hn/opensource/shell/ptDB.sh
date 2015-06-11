  1 #!/bin/sh
  2 func
  3 {
  4         echo "-------------------------start--------------------------------------"
  5         if [ "` dirname "$0" `" = "" ]  || [ "` dirname "$0" `" = "." ] ;then
  6                 CURPATH="`pwd`"
  7         else
  8                 cd "` dirname "$0"`"
  9                 CURPATH="`pwd`"
 10                 cd - > /dev/null 2 >& 1
 11         fi
 12         echo "-------------------------end--------------------------------------"
 13 }
 14 func
 15 su - root -c "psql -d pt < ${CURPATH}/tmp.sql"


