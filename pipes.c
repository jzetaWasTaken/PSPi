#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main() {
  const int SIZE = 512;
  pid_t pid;
  int a[2], b[2], readbytes, status;
  char buffer[SIZE];
  pipe( a );
  pipe( b );
  if ( (pid=fork()) == 0 ) {
    close( a[1] );
    close( b[0] );
    while( (readbytes=read( a[0], buffer, SIZE ) ) > 0)
      write( 1, buffer, readbytes );
    close( a[0] );
    strcpy( buffer, "Hi!...\n" );
    write( b[1], buffer, strlen( buffer ) );
    close( b[1] );
  }
  else {
    close( a[0] );
    close( b[1] );
    strcpy( buffer, "Hello.\n" );
    write( a[1], buffer, strlen( buffer ) );
    close( a[1]);
    while( (readbytes=read( b[0], buffer, SIZE )) > 0)
      write( 1, buffer, readbytes );
    close( b[0]);
    wait(&status);
  }
  exit(0);
}
