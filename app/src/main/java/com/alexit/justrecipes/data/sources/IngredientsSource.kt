package com.alexit.justrecipes.data.sources

import com.alexit.justrecipes.data.model.IngredientModel


interface IngredientsSource {
    val listIngredients: MutableList<IngredientModel>
    val listInputtedIngredients: MutableList<IngredientModel>
}

class IngredientsSourceDB : IngredientsSource {
    override val listIngredients: MutableList<IngredientModel> = mutableListOf(
        IngredientModel(1, "помидор", "овощи"),
        IngredientModel(2, "огурцы", "овощи"),
        IngredientModel(3, "лук", "овощи"),
        IngredientModel(4, "молоко", "молоко и сливки"),
        IngredientModel(5, "кефир", "кисломолочный напиток"),
        IngredientModel(6, "сыр адыгейский","сыр"),
        IngredientModel(7, "сырок плавленый", "сыр"),
        IngredientModel(8, "фосоль", "бобовые"),
        IngredientModel(9, "картофель", "овощи"),
        IngredientModel(10, "морковь", "овощи"),
        IngredientModel(11, "перец", "пряности"),
        IngredientModel(12, "перец болгарский", "овощи"),
        IngredientModel(13, "свинина", "мясо"),
        IngredientModel(14, "яйца", "яйца"),
        IngredientModel(15, "мука пшеничная", "мука"),
        IngredientModel(16, "крахмал", "специи"),
        IngredientModel(17, "соль", "специи"),
        IngredientModel(18, "сахар", "сахар"),
        IngredientModel(19, "укроп", "зелень"),
        IngredientModel(20, "кабачки", "овощи"),
        IngredientModel(21, "форель", "рыба"),
        IngredientModel(22, "колбаса", "колбаса"),
        IngredientModel(23, "макароны", "макароны"),
        IngredientModel(24, "сосиски", "сосиски сардельки колбаски"),
        IngredientModel(25, "хлеб", "хлебобулочный продукт"),
        IngredientModel(26, "свежевыловленная очищенная красная рыба без костей", "рыба"),
        IngredientModel(27, "икра красная", "икра"),
        IngredientModel(28, "морепродукты", "морепродукты"),
        IngredientModel(29, "сметана 25%", "кисломолочный продукт")
    )
    override val listInputtedIngredients: MutableList<IngredientModel> = mutableListOf(
        IngredientModel(40, "томаты консервированные в собственном соку без соли", "овощи"),
        IngredientModel(41, "лосось", "рыба"),
        IngredientModel(42, "свиная отбивная на косточке", "мясо"),
        IngredientModel(43, "морковь по корейски", "овощи"),
        IngredientModel(44, "баранина", "мясо"),
        IngredientModel(45, "фасоль красная", "бобовые"),
        IngredientModel(46, "кефир", "кисломолочный напиток"),
        IngredientModel(47, "яйца куриные", "яйца"),
    )
}

