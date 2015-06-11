import sys
import os
str = '''\
Programing is fun
when the work is done
if you wannameke you re
use Python!'''
f = file('pythonFile.txt', 'a')
f.write(str)
f.close()
