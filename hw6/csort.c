#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

/** Sort VALUES[0], ..., VALUES[N-1] in ascending order in place. */
void sort (double values [ ], int n)
{
  int k, j;
  double temp;
  for (k = 1; k < n; k += 1) {
    /* Elements 0 through k-1 are in nondecreasing order:
     *    values[0] <= values[1] <= ... <= values[k-1].
     * Insert element k into its correct position, so that
     *   values[0] <= values[1] <= ... <= values[k-1]. */
    temp = values[k];
    for (j = k - 1; j >= 0 && values[j] > temp; j -= 1) {
      values[j + 1] = values[j];
    }
    values[j + 1] = temp;
  }
}
        
/** Returns an N-element vector, V, filled with random numbers, with
 *  V[k] = C0 * k + C1 * R, where R is uniform over 0.0 .. 1.0.
 */
double* randomVector(int n, double c0, double c1) 
{
  int j;
  double* values = (double*) malloc(n * sizeof(double));
  for (j = 0; j < n; j += 1) {
    values[j] = c0 * j + c1 * rand() / RAND_MAX;
  }
  return values;
}

static struct timeval startTime;
static void startTiming()
{
  gettimeofday (&startTime, NULL);
}

static void stopTiming()
{
  struct timeval endTime;
  gettimeofday (&endTime, NULL);
  printf ("%5.3f seconds elapsed.\n", 
	  endTime.tv_sec - startTime.tv_sec 
	  + 1e-6 * (endTime.tv_usec - startTime.tv_usec));
}

static void printArr(double values[], int n)
{
  int k;
  for (k=0; k<n; k += 1) {
    printf ("%0.4lg\n", values[k]);
  }
}

int main (int argc, char **argv) {
  int N;
  double * values;
  char type = argc > 2 ? argv[2][0] : 'A';
  if (argc < 2) {
    fprintf (stderr, "Usage: csort SIZE [ TYPE [SEED]] \n");
    exit (1);
  }
  N = atoi (argv[1]);
  srand (argc > 3 ? atol (argv[3]) : 42);

  if (type == 'A')
    values = randomVector(N, 0.0, 1.0);
  else 
    values = randomVector(N, 1.0, 20.0);

  startTiming();
  sort (values, N);
  stopTiming();

  if (N < 20)
    printArr(values, N);
  return 0;
}
