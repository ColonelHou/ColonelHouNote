yum -y install e2fsprogs e2fsprogs-devel
tar -xjf extundelete-0.2.4.tar.bz2
cd extundelete-0.2.4
./configure
make
make install
extundelete /dev/xxx --restore-file /file1.txt
extundelete /dev/xxx --restore-directory /testdir
extundelete /dev/sda3 --restore-file /opt/tmp/xxx.cpp
NOTICE: Extended attributes are not restored.
WARNING: EXT3_FEATURE_INCOMPAT_RECOVER is set.
The partition should be unmounted to undelete any files without further data loss.
If the partition is not currently mounted, this message indicates
it was improperly unmounted, and you should run fsck before continuing.
If you decide to continue, extundelete may overwrite some of the deleted
files and make recovering those files impossible.  You should unmount the
file system and check it with fsck before using extundelete.
Would you like to continue? (y/n)
y
Loading filesystem metadata ... 2334 groups loaded.
Loading journal descriptors ... 29510 descriptors loaded.
Successfully restored file /opt/tmp/xxx.cpp



http://www.111cn.net/sys/CentOS/61250.htm
