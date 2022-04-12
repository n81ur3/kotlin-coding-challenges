package com.igorwojda.binarytree.validate

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

private fun isValidSearchBinaryTree(node: Node<Int>): Boolean {
    if (node.left == null && node.right == null) {
        return true
    } else {
        if (node.left?.right != null && node.right == null) return false
        if (node.right?.left != null && node.left == null) return false
    }

    var leftBranch = true
    var rightBranch = true
    node.left?.run {
        if (data > node.data) return false
        leftBranch = isValidSearchBinaryTree(this)
    }
    node.right?.run {
        if (data < node.data) return false
        rightBranch = isValidSearchBinaryTree(this)
    }

    return leftBranch && rightBranch
}

private class Test {
    @Test
    fun `Validate recognizes a valid BST`() {
        // -- -------Tree------------
        //
        //           10
        //          /  \
        //         5    15
        //        /       \
        //       0         20
        // --------------------------

        val node = Node(10)
        node.insert(5)
        node.insert(15)
        node.insert(0)
        node.insert(20)

        isValidSearchBinaryTree(node) shouldBeEqualTo true
    }

    @Test
    fun `Validate recognizes an invalid rightNode`() {
        // -- -------Tree------------
        //
        //           10
        //          /  \
        //         5    15
        //        / \     \
        //       0   2     20
        // --------------------------

        val node = Node(10)
        node.insert(5)
        node.insert(15)
        node.insert(0)
        node.insert(20)
        node.left?.right = Node(2)

        isValidSearchBinaryTree(node) shouldBeEqualTo false
    }

    @Test
    fun `Validate recognizes an invalid BST`() {
        // -- -------Tree------------
        //
        //           10
        //          /  \
        //         5    15
        //        /       \
        //       0         20
        //        \
        //        999
        // --------------------------

        val node = Node(10)
        node.insert(5)
        node.insert(15)
        node.insert(0)
        node.insert(20)
        node.left?.left?.right = Node(999)

        isValidSearchBinaryTree(node) shouldBeEqualTo false
    }
}

private data class Node<E : Comparable<E>>(
    var data: E,
    var left: Node<E>? = null,
    var right: Node<E>? = null
) {
    fun insert(e: E) {
        if (e < data) { // left node
            if (left == null) {
                left = Node(e)
            } else {
                left?.insert(e)
            }
        } else if (e > data) { // right node
            if (right == null) {
                right = Node(e)
            } else {
                right?.insert(e)
            }
        }
    }
}
