// signalfork.c
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <signal.h>

void trapper(int sig) {
	printf("SIGUSR1\n");
}

int main() {
	pid_t parent, child;
	parent = getpid();
	signal( SIGUSR1, trapper );
	if ( (child = fork()) == 0 ) { 
		/* child */
		sleep(1);
		kill(parent, SIGUSR1);
		sleep(1);
		kill(parent, SIGUSR1);
		sleep(1);
		kill(parent, SIGUSR1);
		sleep(1);
		kill(parent, SIGKILL);
		exit(0);
	}
	else { 
		/* parent */
		for (;;);
	}
	return 0;
}
