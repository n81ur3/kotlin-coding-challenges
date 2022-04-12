package com.igorwojda.tree.heap.maxbinaryheap

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

private class MaxBinaryHeap<E : Comparable<E>> {
    val items = mutableListOf<E>()

    fun add(element: E) {
        items.add(element)
        heapify(items.size - 1)
    }

    fun removeMax(): E? {
        if (isEmpty()) return null
        items.swap(0, items.size - 1)
        val result = items.removeAt(items.size - 1)
        heapify(items.size - 1)
        return result
    }

    private fun getParentIndex(index: Int): Int {
        if (index % 2 == 0) return (index - 2) / 2
        else return (index - 1) / 2
    }

    private fun getLeftChildIndex(index: Int): Int = (index * 2) + 1

    private fun getRightChildIndex(index: Int): Int = (index * 2) + 2

    private fun heapify(index: Int) {
        val parentIndex = getParentIndex(index)
        if (parentIndex < 0) return
        if (items[parentIndex] < items[index]) {
            items.swap(parentIndex, index)
        }
        heapify(parentIndex)
    }

    fun isEmpty(): Boolean = items.isEmpty()

    private fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]
        this[index1] = this[index2]
        this[index2] = tmp
    }
}

private class Test {
    @Test
    fun `build valid max binary heap`() {
        MaxBinaryHeap<Int>().apply {
            add(9)
            add(7)
            add(6)
            add(3)
            add(2)
            add(4)

            // ----------Heap------------
            //
            //           9
            //         /   \
            //        7     6
            //       / \   /
            //      3   2 4
            //
            // --------------------------

            items[0] shouldBeEqualTo 9
            items[1] shouldBeEqualTo 7
            items[2] shouldBeEqualTo 6
            items[3] shouldBeEqualTo 3
            items[4] shouldBeEqualTo 2
            items[5] shouldBeEqualTo 4
            items.size shouldBeEqualTo 6
        }
    }

    @Test
    fun `bubble-up added element one level up`() {
        MaxBinaryHeap<Int>().apply {
            add(9)
            add(7)
            add(6)
            add(3)
            add(2)
            add(4)

            // ----------Heap------------
            //
            //           9
            //         /   \
            //        7     6
            //       / \   /
            //      3   2 4
            //
            // --------------------------

            add(8) // and bubble it up

            // ----------Heap------------
            //
            //           9
            //         /   \
            //        7     8
            //       / \   / \
            //      3   2 4   6
            //
            // --------------------------

            items[0] shouldBeEqualTo 9
            items[1] shouldBeEqualTo 7
            items[2] shouldBeEqualTo 8
            items[3] shouldBeEqualTo 3
            items[4] shouldBeEqualTo 2
            items[5] shouldBeEqualTo 4
            items[6] shouldBeEqualTo 6
            items.size shouldBeEqualTo 7
        }
    }

    @Test
    fun `bubble-up added element to the root`() {
        MaxBinaryHeap<Int>().apply {
            add(9)
            add(7)
            add(6)
            add(3)
            add(2)
            add(4)

            // ----------Heap------------
            //
            //           9
            //         /   \
            //        7     6
            //       / \   /
            //      3   2 4
            //
            // --------------------------

            add(12) // and bubble it up

            // ----------Heap------------
            //
            //           12
            //         /    \
            //        7      9
            //       / \    / \
            //      3   2  4   6
            //
            // --------------------------

            items[0] shouldBeEqualTo 12
            items[1] shouldBeEqualTo 7
            items[2] shouldBeEqualTo 9
            items[3] shouldBeEqualTo 3
            items[4] shouldBeEqualTo 2
            items[5] shouldBeEqualTo 4
            items[6] shouldBeEqualTo 6
            items.size shouldBeEqualTo 7
        }
    }

    @Test
    fun `remove max element 9`() {
        MaxBinaryHeap<Int>().apply {
            add(9)
            add(7)
            add(6)
            add(3)
            add(2)
            add(4)

            // ----------Heap------------
            //
            //           9
            //         /   \
            //        7     6
            //       / \   /
            //      3   2 4
            //
            // --------------------------

            removeMax() shouldBeEqualTo 9

            // ----------Heap------------
            //
            //           7
            //         /   \
            //        4     6
            //       / \
            //      3   2
            //
            // --------------------------

            items[0] shouldBeEqualTo 7
            items[1] shouldBeEqualTo 4
            items[2] shouldBeEqualTo 6
            items[3] shouldBeEqualTo 3
            items[4] shouldBeEqualTo 2
            items.size shouldBeEqualTo 5
        }
    }

    @Test
    fun `remove max element 8`() {
        MaxBinaryHeap<Int>().apply {
            add(8)
            add(7)
            add(6)
            add(3)
            add(2)
            add(4)

            // ----------Heap------------
            //
            //           8
            //         /   \
            //        7     6
            //       / \   /
            //      3   2 4
            //
            // --------------------------

            removeMax() shouldBeEqualTo 8

            // ----------Heap------------
            //
            //           7
            //         /   \
            //        4     6
            //       / \
            //      3   2
            //
            // --------------------------

            items[0] shouldBeEqualTo 7
            items[1] shouldBeEqualTo 4
            items[2] shouldBeEqualTo 6
            items[3] shouldBeEqualTo 3
            items[4] shouldBeEqualTo 2
            items.size shouldBeEqualTo 5
        }
    }

    @Test
    fun `remove max element until heap is empty`() {
        MaxBinaryHeap<Int>().apply {
            add(8)
            add(7)
            add(6)

            // ----------Heap------------
            //
            //           8
            //         /   \
            //        7     6
            //
            // --------------------------

            removeMax() shouldBeEqualTo 8

            // ----------Heap------------
            //
            //           7
            //         /
            //        6
            //
            // --------------------------

            items[0] shouldBeEqualTo 7
            items[1] shouldBeEqualTo 6
            items.size shouldBeEqualTo 2

            removeMax() shouldBeEqualTo 7

            // ----------Heap------------
            //
            //           6
            //
            // --------------------------

            items[0] shouldBeEqualTo 6
            items.size shouldBeEqualTo 1

            removeMax() shouldBeEqualTo 6
            items.size shouldBeEqualTo 0
        }
    }
}
