Assignment 1 — Divide & Conquer
Learning goals

Implement classic divide-and-conquer algorithms with safe recursion patterns.

Analyze running-time recurrences (Master theorem, Akra–Bazzi) and validate with measurements.

Collect metrics (time, depth, comparisons/allocations) and communicate results via a short report and clean Git history.


Project structure

src/
main/java/edu/aitu/dsa/
algo/
sort/
MergeSort.java
QuickSort.java
select/
DeterministicSelect.java
geometry/
ClosestPair.java
Point.java
metrics/
Metrics.java
CsvLogger.java
cli/
Bench.java
test/java/edu/aitu/dsa/
algo/sort/
MergeSortTest.java
QuickSortTest.java
QuickSortDepthTest.java
algo/select/
DeterministicSelectTest.java
geometry/
ClosestPairTest.java


How to build & run (benches)

Build the project:

mvn -q -DskipTests package


Run benchmarks (each appends rows to metrics.csv):

java -cp target/assign1-dc-1.0.0.jar edu.aitu.dsa.cli.Bench mergesort 1000 10000 1000 3
java -cp target/assign1-dc-1.0.0.jar edu.aitu.dsa.cli.Bench quicksort 1000 10000 1000 3
java -cp target/assign1-dc-1.0.0.jar edu.aitu.dsa.cli.Bench select   1000 10000 1000 3
java -cp target/assign1-dc-1.0.0.jar edu.aitu.dsa.cli.Bench closest  1000 10000 1000 3


CSV format:

algo,n,time_ns,depth,comparisons,swaps,allocs


Architecture notes

Metrics: global tracker collects time, recursion depth, comparisons, swaps, allocations.

MergeSort: single reusable buffer (allocs≈n once), linear merge; cutoff to insertion sort.

QuickSort: randomized pivot; recurse only into smaller side, larger side via loop → stack O(log n).

Select (MoM5): groups of 5, median-of-medians pivot; in-place partition; recurse only into the needed side.

Closest Pair: presort by x; divide recursively; in strip check ≤7–8 neighbors by y-order.


Recurrence analysis

MergeSort: T(n)=2T(n/2)+Θ(n) → Master theorem Case 2 → Θ(n log n). Depth = Θ(log n).

QuickSort (avg.): T(n)=T(αn)+T((1−α)n)+Θ(n) with E[α]≈1/2 → expected Θ(n log n), worst Θ(n²). Smaller-first recursion bounds stack.

Select (MoM5): T(n)=T(n/5)+T(7n/10)+Θ(n) → Θ(n) (Akra–Bazzi).

Closest Pair: T(n)=2T(n/2)+Θ(n) → Θ(n log n).

Plots (from Google Sheets)

Time vs n
Depth vs n
Comparisons and Swaps

Summary (theory vs measurements)

MergeSort / QuickSort times grow ≈ n log n; QuickSort shows higher variance but median matches theory.

QuickSort recursion depth is logarithmic, confirmed by tests.

Select shows linear growth; constants visible on small n.

Closest Pair scales ≈ n log n with higher constants due to geometry.

Small mismatches explained by cache effects, GC, cutoff.

Testing (10%)

Run:

mvn test


Covers:

Sorting correctness on random/adversarial inputs.

QuickSort depth ≤ ~2*floor(log2 n)+O(1).

Select vs Arrays.sort(a)[k] across 100 random trials.

Closest Pair vs brute-force O(n²) up to n=2000.

GitHub workflow (20%)

Branches

main — only working releases (v0.1, v1.0).

Features: feature/mergesort, feature/quicksort, feature/select, feature/closest, feature/metrics, feature/cli, feature/tests, feature/docs.

Commit storyline

init: maven, junit5, readme

feat(metrics): counters, depth tracker, CSV writer

feat(mergesort): baseline + reuse buffer + cutoff + tests

feat(quicksort): smaller-first recursion, randomized pivot + tests

refactor(util): partition, swap, shuffle, guards

feat(select): deterministic select (MoM5) + tests

feat(closest): divide-and-conquer implementation + tests

feat(cli): parse args, run algos, emit CSV

docs(report): master cases & AB intuition, plots

fix: edge cases (duplicates, tiny arrays)

release: v1.0

## Summary — theory vs measurements

- **MergeSort**: время растёт ≈ `n log n`; кривая гладкая. Линейный merge + единый буфер → низкие аллокации (allocs ≈ n один раз).
- **QuickSort**: среднее ≈ `n log n`, но разброс выше из-за рандомного пивота. При этом глубина рекурсии логарифмическая: тесты показали `depth ≤ ~2⌊log2 n⌋ + O(1)`.
- **Select (Median-of-Medians)**: линейный тренд `Θ(n)`; на малых n заметны константы (группировка по 5, несколько проходов).
- **Closest Pair**: масштабируется ≈ `n log n`; константы выше из-за сортировок и проверки “strip”.
- **Сравнение сравнений/перестановок**: у MergeSort число сравнений растёт стабильно, перестановок почти нет; у QuickSort — больше сравнений/перестановок и сильнее варьирует по запускам.
- **Память/аллокации**: MergeSort — предсказуемые аллокации (буфер); QuickSort/Select работают in-place (allocs ~ 0).
- **Причины расхождений**: кэш-локальность, GC, рандомизация пивота, small-n cutoff и накладные расходы на измерения.
- **Итог**: эмпирика согласуется с теорией — `MergeSort/QuickSort ~ n log n`, `Select ~ n`, `Closest ~ n log n`; ограничения по глубине рекурсии соблюдены.
