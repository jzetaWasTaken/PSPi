#include <unistd.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
void check(int sig) {
	char resp;
	printf("Finish? (y/n): ");
	scanf("%c", &resp);
	if (resp == 'y') 
		exit(0);
}
int main(void) {
	int i;
	signal(SIGINT, SIG_IGN);
	for (i=0; i<10; i++) {
		printf("Ignoring ^C\n");
		sleep(2);
	}
	signal(SIGINT, SIG_DFL);
	for (i=0; i<10; i++) {
		printf("I do not ignore ^C\n");
	sleep(2);
	}
	signal(SIGINT, check);
	for (i=0; i<10; i++) {
		printf("Whatever you want\n");
	sleep(2);
	}
	exit(0);
}
