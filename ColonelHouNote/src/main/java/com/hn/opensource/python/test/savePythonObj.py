# -*- coding: utf-8 -*-
import cPickle
fileName = 'test.txt'
def initData():
    data1 = ['abc', 12, 32]
    data2 = {1:'a', "b":'dad'}
    data3 = (1, 2 ,3)
    result = (data1, data2, data3)
    #return (data1, data2, data3)外面使用元组来接收
    return result
def writeData():
    initData()
    outputFile = open(fileName, 'w')
    cPickle.dump(initData()[0], outputFile)
    cPickle.dump(initData()[1], outputFile)
    cPickle.dump(initData()[2], outputFile)
    outputFile.close()
def readData():
    writeData()
    inputFile = open(fileName, 'rb')
    print cPickle.load(inputFile)
    print cPickle.load(inputFile)
    print cPickle.load(inputFile)
readData()
outStr = cPickle.dumps(initData()[0])
print outStr
open('new.txt', 'wb').write(outStr)
