package com.igorwojda.tree.binarysearchtree

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

private class BinarySearchTree<E : Comparable<E>> {
    var root: BinaryNode<E>? = null
        private set

    fun add(element: E) {

        if (contains(element)) return

        val node = BinaryNode(element)
        if (root == null) {
            root = node
            return
        }
        var prev = root
        var tmp = root
        while (tmp != null) {
            prev = tmp
            if (element < tmp.data) {
                tmp = tmp.left
            } else {
                tmp = tmp.right
            }
        }
        if (element < prev!!.data) {
            prev.left = node
        } else {
            prev.right = node
        }
    }

    fun contains(element: E): Boolean {
        var tmp = root
        while (true) {
            when {
                tmp == null -> return false
                tmp.data == element -> return true
                element < tmp.data -> tmp = tmp.left
                else -> tmp = tmp.right
            }
        }
    }

    fun isEmpty(): Boolean = root == null
}

private data class BinaryNode<E : Comparable<E>>(
    val data: E,
    var left: BinaryNode<E>? = null,
    var right: BinaryNode<E>? = null
)

private class Test {
    @Test
    fun `build valid binary search tree`() {
        // ----------Tree------------
        //
        //           6
        //         /   \
        //        3     8
        //       / \   / \
        //      1   5 7   9
        //
        // --------------------------

        BinarySearchTree<Int>().apply {
            add(6)
            add(3)
            add(8)
            add(5)
            add(7)
            add(1)
            add(9)

            root?.data shouldBeEqualTo 6
            root?.left?.data shouldBeEqualTo 3
            root?.left?.left?.data shouldBeEqualTo 1
            root?.left?.left?.left shouldBeEqualTo null
            root?.left?.left?.right shouldBeEqualTo null
            root?.left?.right?.data shouldBeEqualTo 5
            root?.left?.right?.left shouldBeEqualTo null
            root?.left?.right?.right shouldBeEqualTo null
            root?.right?.data shouldBeEqualTo 8
            root?.right?.left?.data shouldBeEqualTo 7
            root?.right?.left?.left shouldBeEqualTo null
            root?.right?.left?.right shouldBeEqualTo null
            root?.right?.right?.data shouldBeEqualTo 9
            root?.right?.right?.left shouldBeEqualTo null
            root?.right?.right?.right shouldBeEqualTo null
        }
    }

    @Test
    fun `duplicated value is not added`() {
        // ----------Tree------------
        //
        //           6
        //         /   \
        //        3     8
        //
        // --------------------------

        BinarySearchTree<Int>().apply {
            add(6)
            add(3)
            add(8)
            add(6)
            add(3)
            add(8)

            root?.data shouldBeEqualTo 6
            root?.left?.data shouldBeEqualTo 3
            root?.left?.left shouldBeEqualTo null
            root?.left?.right shouldBeEqualTo null
            root?.right?.data shouldBeEqualTo 8
            root?.right?.left shouldBeEqualTo null
            root?.right?.right shouldBeEqualTo null
        }
    }

    @Test
    fun `tree contains all elements`() {
        // ----------Tree------------
        //
        //           6
        //         /   \
        //        3     8
        //       / \   / \
        //      1   5 7   9
        //
        // --------------------------

        BinarySearchTree<Int>().apply {
            add(6)
            add(3)
            add(8)
            add(5)
            add(7)
            add(1)
            add(9)

            contains(6) shouldBeEqualTo true
            contains(3) shouldBeEqualTo true
            contains(8) shouldBeEqualTo true
            contains(5) shouldBeEqualTo true
            contains(7) shouldBeEqualTo true
            contains(1) shouldBeEqualTo true
            contains(9) shouldBeEqualTo true
            contains(100) shouldBeEqualTo false
        }
    }
}
