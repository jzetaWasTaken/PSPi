// trapper.c 
#include <signal.h>
#include <stdio.h>
#include <unistd.h>
void trapper(int);
int main()
{
	int i;
	signal(9, trapper);
	signal(12, trapper);
	signal(18, trapper);
	signal(19, trapper);
	printf("Process identifier: %d\n", getpid() );
	pause();
	printf("Continuing...\n");
	printf("Finished.\n");
	return 0;
}
void trapper(int sig)
{
	printf("\nHello\n");
}
