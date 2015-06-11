c="153475)8972432343"
echo $c
if [[ "$c" =~ ^[0-9]+$ ]]; then

    echo "Number"
else
    echo "Not a number!"
fi