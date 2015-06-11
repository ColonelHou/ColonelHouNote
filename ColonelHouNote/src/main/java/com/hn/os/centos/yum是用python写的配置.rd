[hadoop@hadoop /var/www/html]$ls -lha /usr/bin/pyth*
lrwxrwxrwx  1 root root   36 Mar  9 16:29 /usr/bin/python -> /usr/local/python2.7.3/bin/python2.7
lrwxrwxrwx. 1 root root    6 Oct 28 14:57 /usr/bin/python2 -> python
-rwxr-xr-x  2 root root 8.9K Nov 22  2013 /usr/bin/python2.6
-rwxr-xr-x  2 root root 8.9K Nov 22  2013 /usr/bin/python_old

vim /usr/bin/yum

修改头#!/usr/bin/python2.6  => #!/usr/bin/python2.4