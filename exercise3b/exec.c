// exec.c
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
main() {
	
	// Call to exec function
	execl("/bin/mkdir", "mkdir", "work", NULL);
	
	printf("I should not arrive here\n");	
	exit(-1);
}
