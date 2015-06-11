10 0 * * * mysqldump -uroot -proot -hlocalhost traceweb | gzip > "path_"$(/bin/date -d yesterday '+\%Y\%m\%d')".sql.gz"
