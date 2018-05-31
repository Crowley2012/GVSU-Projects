//---------------------------------------------------------------
// File: Code202_Tree.h
// Purpose: Header file for a demonstration of a binary tree
// Programming Language: C++
// Author: Dr. Rick Coleman
//---------------------------------------------------------------
#ifndef CODE202_TREE_H
#define CODE202_TREE_H
#include <iostream>

using namespace std;

// Define a structure to be used as the tree node
struct TreeNode
{
    int      Key;
    float    fValue;
    int      iValue;
    char     cArray[7];
    TreeNode *left;
    TreeNode *right;
};

class Code202_Tree
{
    private:
        TreeNode *root;

    public:
        Code202_Tree();
        ~Code202_Tree();
        bool isEmpty();
        TreeNode *SearchTree(int Key);
        int Insert(TreeNode *newNode);
        int Insert(int Key, float f, int i, char *cA);
        int Delete(int Key);
        void PrintOne(TreeNode *T);
        void PrintTree();
    private:
        void ClearTree(TreeNode *T);
        TreeNode *DupNode(TreeNode * T);
        void PrintAll(TreeNode *T);
};

#endif
