//alarm2.c
#include <stdlib.h>
#include <stdio.h>
#include <signal.h>
#include <unistd.h>

void trapper(int);
int main() {
	int i;
	signal(14, trapper);
	printf("Process identifier: %d\n", getpid() );

	alarm(5);
	pause();
	alarm(3);
	pause();
	for(;;) {
		alarm(1);
		pause();
	}
	return 0;
}
void trapper(int sig) {
	printf("RIIIIIIIIING!\n");
}
