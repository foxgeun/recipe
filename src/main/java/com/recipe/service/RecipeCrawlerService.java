package com.recipe.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipe.entity.Recipe;
import com.recipe.entity.RecipeIngre;
import com.recipe.entity.RecipeOrder;
import com.recipe.repository.RecipeIngreRepository;
import com.recipe.repository.RecipeOrderRepository;
import com.recipe.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeCrawlerService {

    @Autowired
    private final RecipeRepository recipeRepository;
    private final RecipeIngreRepository recipeIngreRepository;
    private final RecipeOrderRepository recipeOrderRepository;
    
    int count = 0;

    public void crawlAndSaveRecipes() throws IOException {
        String url = "https://wtable.co.kr/recipes";
        Document doc = Jsoup.connect(url).get();

        Elements recipes = doc.select("a:has(.erZvWP)");

        for (Element recipe : recipes) {
            String subTitle = recipe.select(".LxJcT").text();
            String title = recipe.select(".hpYiJK").text();
            String imageUrl = recipe.select(".duQJWI").attr("src");
            String words = recipe.select(".MiXMV").text();
            String level = words.split(" ")[0];
            String time = words.split(" ")[1];

         
            
            Recipe recipeObject = new Recipe();
            recipeObject.setSubTitle(subTitle);
            recipeObject.setTitle(title);
            recipeObject.setImageUrl(imageUrl);
            recipeObject.setLevel(level);
            recipeObject.setDurTime(time);

           
            recipeRepository.save(recipeObject);
          
            crawlAndSaveDetailPage(recipeObject, recipe.select("a").attr("href"));
        }
    }

    private void crawlAndSaveDetailPage(Recipe recipeObject, String detailUrl) throws IOException {
        if (!detailUrl.startsWith("http://")) {
            detailUrl = "https://wtable.co.kr" + detailUrl;
        }

        Document detailDoc = Jsoup.connect(detailUrl).get();

        String description = detailDoc.select(".IdQIJ").text();
        String intro = detailDoc.select(".ksodYd").text();
        
        String IngreImg = detailDoc.selectFirst(".ingredient").select("img").attr("src");
       
        Element parentElement = detailDoc.selectFirst(".igroups");
        Elements basicIngres = parentElement.select("ul > li:first-child > ul li");
        Elements seasonIngres = parentElement.select("ul > li:last-child > ul li");
        
        
        count++;
        //조리순서 저장
        Elements recipeOrderDivs = detailDoc.select(".ihCzrN");
        for(Element recipeOrderDiv : recipeOrderDivs) {
            String imgSrc =  recipeOrderDiv.select("img").attr("src");
            String ex = recipeOrderDiv.select(".enJPxd").text();
            RecipeOrder recipeOrder = new RecipeOrder();
            
            recipeOrder.setOrderNumber(count);
            
            recipeOrder.setImgUrl(imgSrc);
            recipeOrder.setContent(ex);
            
            
            
            
            recipeOrderRepository.save(recipeOrder);
        }
        
        

        
        
        
        
        RecipeIngre recipeIngre;
        
        
        // 기본 재료 저장
        for (Element basic_ingre : basicIngres) {
            recipeIngre = new RecipeIngre();
            String ingre = basic_ingre.text();
            recipeIngre.setIngreImg(IngreImg);
            recipeIngre.setRecipeCount(count);
            recipeIngre.setBasicIngreName(ingre);
            recipeIngre.setRecipe(recipeObject);
            recipeIngreRepository.save(recipeIngre);
            

      
        }
        

        //중복된 데이터 판별
        if(!seasonIngres.equals(basicIngres)) {
        // 양념 재료 저장
        for (Element season_ingre : seasonIngres) {
            recipeIngre = new RecipeIngre();
            String ingre = season_ingre.text();
            recipeIngre.setRecipeCount(count);
            recipeIngre.setSeasonIngreName(ingre);
            recipeIngre.setRecipe(recipeObject);
            recipeIngreRepository.save(recipeIngre);
        }
        }

        recipeObject.setDescription(description);
        recipeObject.setIntro(intro);

        // 완성된 Recipe 객체 저장
        recipeRepository.save(recipeObject);
    }
        
        
        
        
        
    
}