import sys

class ShortInputException(Exception):
    '''A user-defined exception class.'''
    def __init__(self, length, asLeast):
        Exception.__init__(self)
        self.length = length
        self.asLeast = asLeast
try:
    s = raw_input('Enter something - >')
    if len(s) < 3:
        raise ShortInputException(len(s), 3)
    elif len(s) == 5:
        raise RuntimeError("Run error")
    else:
        print 'No exception was raised.'
except EOFError:
    print 'Why did you do a EOFError on me?'
except ShortInputException, x:
    print 'ShortInputException:The input was of length %d, was expecting at least %d' %(x.length, x.asLeast)
finally:
    print 'Some other Exception'

