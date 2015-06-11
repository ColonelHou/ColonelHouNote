def add100(x):
    return x + 100
hh = [11, 22, 33]
#print map(add100, hh)
#print [add100(i) for i in hh]
re = map(lambda x: x + 100, hh)
#print re
def abc(a, b, c):
    return a * 100 + b * 10 + c
list1 = [2, 3, 4]
list2 = [2, 3, 4]
list3 = [2, 3, 4]
#print map(abc, list1, list2, list3)
#print map(lambda a, b, c : a * 100 + b * 10 + c, list1,list2,list3)
#print map(None, list1, list2, list3)
def add(x, y):return x + y
#print reduce(add, range(1, 11), 15)
#print filter(lambda x : x % 2 != 0, range(1, 5))
#print filter(lambda s : len(s) != 0, 'hello')
#print reduce((lambda x, y: x - y), range(1, 3), 20)
exec 'print "Hello World"'
i = []
i.append('item')
print `i`
print repr(i)
