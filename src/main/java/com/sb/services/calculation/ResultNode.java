package com.sb.services.calculation;

import com.sb.commands.TransactionCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Kingsley Kumar on 25/09/2016.
 */
public class ResultNode {

    protected String name;
    protected double amount = 0;
    protected double amountInPercentage = 0;
    protected List<ResultNode> children = new ArrayList<>();

    public ResultNode(String name) {

        this.name = name;
    }

    public ResultNode setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public ResultNode setAmountInPercentage(double amountInPercentage) {
        this.amountInPercentage = amountInPercentage;
        return this;
    }

    public void setChildren(List<ResultNode> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountInPercentage() {
        return amountInPercentage;
    }

    public List<ResultNode> getChildren() {
        return children;
    }

    protected void addTransaction(TransactionCommand tx) {

        // Total Row
        double value = getValue(tx);

        double totalAmount = this.amount + value;

        setAmount(totalAmount);

        // Category Row
        String category = (tx.getCategory() == null || tx.getCategory().trim().isEmpty()) ? "Undefined" : tx
                .getCategory();

        ResultNode categoryNode = addChildResultNode(tx, category, this, totalAmount);

        // SubCategory Row
        String subCategory = (tx.getSubcategory() == null || tx.getSubcategory().trim().isEmpty()) ? "Undefined" : tx
                .getSubcategory();

        ResultNode subCategoryNode = addChildResultNode(tx, subCategory, categoryNode, totalAmount);//categoryNode.getAmount());

        //Leaf Node
//        ResultNode leafNode = new ResultNode(tx.getItem());
        ResultNode leafNode = createResultNode(tx.getItem() + " [" + tx.getDate() + "]");

        leafNode.setAmount(getValue(tx));

//        updatePercentage(leafNode, subCategoryNode.getAmount());

        subCategoryNode.getChildren().add(leafNode);

//        subCategoryNode.getChildren().forEach(e -> updatePercentage(e, totalAmount)); //subCategoryNode.getAmount()));
    }

    protected ResultNode addChildResultNode(TransactionCommand tx, String name, ResultNode parentNode, double parentTotal) {

//        if(name.isEmpty()) {
//
//            return parentNode;
//        }

        List<ResultNode> children = parentNode.getChildren();

        Optional<ResultNode> nodeOptional = parentNode.children
                .stream()
                .filter(node -> name.equals(node.getName()))
                .findFirst();
        ResultNode node;

        if (nodeOptional.isPresent()) {

            node = nodeOptional.get();

        } else {

            node = createResultNode(name);

            children.add(node);
        }

        double currentAmount = node.getAmount() + getValue(tx);

        node.setAmount(currentAmount);
//
//        if (name.equals("Groceries")) {
//
//            System.out.println("####### parentTotal = " + parentTotal);
//            System.out.println("####### currentAmount = " + currentAmount + " ; " + ((node
//                    .getAmount() * 100) / parentTotal));
//        }

        return node;
    }


    /**
     * Sort the children by higest percentage to lowest.
     */
    public void sortNodesByHighestPercentage() {

        Collections.sort(children, (o1, o2) -> Double.compare(o2.getAmountInPercentage(), o1.getAmountInPercentage()));

        children.forEach(e -> e.sortNodesByHighestPercentage());
    }

    /**
     * If there is only one undefined category/subcategory exists, then it is redundant. So this method detaches undefined node and takes all its children and
     * moves the children into parent of undefined node.
     */
    public void updateUndefinedCategories() {

        if (children.size() == 1) {

            ResultNode childNode = children.get(0);

            if (childNode.getName().equals("Undefined")) {

                children.clear();

                childNode.getChildren().forEach(child -> {

                    children.add(child);

//                    updatePercentage(child, getAmount());
                });
            } else {

                children.forEach(e -> e.updateUndefinedCategories());
            }
        } else {

            children.forEach(e -> e.updateUndefinedCategories());
        }
    }

    public void updatePercentage() {

        updatePercentage(this, getAmount());
    }

    private void updatePercentage(ResultNode node, double totalAmount) {

        node.getChildren().forEach(e -> {

            double percentage = ((e.getAmount() * 100) / totalAmount);

            e.setAmountInPercentage(percentage);

            if (e.getChildren().size() > 0) {

                updatePercentage(e, totalAmount);
            }
        });

    }

//    private void updatePercentage(ResultNode node, double totalAmount) {
//
//        double percentage = ((node.getAmount() * 100) / totalAmount);
//
//        node.setAmountInPercentage(percentage);
//    }

    protected double getValue(TransactionCommand tx) {

        double value = 0;

        try {

            value = Double.parseDouble(tx.getValue());
        } catch (NumberFormatException e) {

            System.out.println("[ResultNode] Exception while parsing value from Transaction");
        }
        return value;
    }

    protected ResultNode createResultNode(String name) {

        return new ResultNode(name);
    }
}
