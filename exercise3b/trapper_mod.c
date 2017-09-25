// trapper.c 
#include <signal.h>
#include <stdio.h>
#include <unistd.h>
void trapper(int);
int main()
{
	int i;
	for(i=1;i<=9;i++)
	signal(i, trapper);
	printf("Process identifier: %d\n", getpid() );
	pause();
	printf("Continuing...\n");
	printf("Finished.\n");
	return 0;
}
void trapper(int sig)
{
	printf("\nReceived signal: %d\n", sig);
}
