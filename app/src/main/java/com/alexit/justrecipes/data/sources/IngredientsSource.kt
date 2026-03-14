package com.alexit.justrecipes.data.sources

import com.alexit.justrecipes.data.model.IngredientModel


interface IngredientsSource {
    val listIngredients: MutableList<IngredientModel>
    val listInputtedIngredients: MutableList<IngredientModel>
}

class IngredientsSourceDB : IngredientsSource {
    override val listIngredients: MutableList<IngredientModel> = mutableListOf(
        IngredientModel(1, "помидор", null, "овощи"),
        IngredientModel(2, "огурцы", null, "овощи"),
        IngredientModel(3, "лук", null, "овощи"),
        IngredientModel(4, "молоко", null, "молоко и сливки"),
        IngredientModel(5, "кефир", null, "кисломолочный напиток"),
        IngredientModel(6, "сыр адыгейский", null, "сыр"),
        IngredientModel(7, "сырок плавленый", null, "сыр"),
        IngredientModel(8, "фосоль", null, "бобовые"),
        IngredientModel(9, "картофель", null, "овощи"),
        IngredientModel(10, "морковь", null, "овощи"),
        IngredientModel(11, "перец", null, "пряности"),
        IngredientModel(12, "перец болгарский", null, "овощи"),
        IngredientModel(13, "свинина", null, "мясо"),
        IngredientModel(14, "яйца", null, "яйца"),
        IngredientModel(15, "мука пшеничная", null, "мука"),
        IngredientModel(16, "крахмал", null, "специи"),
        IngredientModel(17, "соль", null, "специи"),
        IngredientModel(18, "сахар", null,  "сахар"),
        IngredientModel(19, "укроп", null, "зелень"),
        IngredientModel(20, "кабачки", null, "овощи"),
        IngredientModel(21, "форель", null, "рыба"),
        IngredientModel(22, "колбаса", null, "колбаса"),
        IngredientModel(23, "макароны", null, "макароны"),
        IngredientModel(24, "сосиски", null, "сосиски сардельки колбаски"),
        IngredientModel(25, "хлеб", null, "хлебобулочный продукт"),
        IngredientModel(26, "свежевыловленная очищенная красная рыба без костей", null, "рыба"),
        IngredientModel(27, "икра красная", null, "икра"),
        IngredientModel(28, "морепродукты", null, "морепродукты"),
        IngredientModel(29, "сметана 25%", null, "кисломолочный продукт")
    )
    override val listInputtedIngredients: MutableList<IngredientModel> = mutableListOf(
        IngredientModel(1, "томаты консервированные в собственном соку без соли", null, "овощи"),
        IngredientModel(2, "лосось", null, "рыба"),
        IngredientModel(3, "свиная отбивная на косточке", null, "мясо"),
        IngredientModel(4, "морковь по корейски", null, "овощи"),
        IngredientModel(5, "баранина", null, "мясо"),
        IngredientModel(6, "фасоль красная", null, "бобовые"),
        IngredientModel(7, "кефир", null, "кисломолочный напиток"),
        IngredientModel(8, "яйца куриные", null, "яйца"),
    )
}

