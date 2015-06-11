s="a,b,d"
IFS=,
ss=($s)
for s in ${ss[@]}
do
    echo "$s"
done
获取数组所有数据：${arr[*]}
for x in ${arrTbls[@]}; do