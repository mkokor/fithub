import "../../css/Nutrition.css";
import LoadingSpinner from "../LoadingSpinner";
import MealPlan from "./MealPlan";
import { useState, useEffect } from "react";
import { getMealPlan } from "../../api/ClientApi";
import { jsPDF } from "jspdf";
import "jspdf-autotable";
import { useAuth } from '../../context/AuthContext';

const Nutrition = () => {
  const [mealplan, setMealPlan] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const { user } = useAuth(); 

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (user) {
          const data = await getMealPlan();
          console.log(data);
          setMealPlan(data);
        }
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching mealplan data:", error);
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  function downloadMealplan() {
    const doc = new jsPDF();
    let y = 10;

    doc.setFontSize(16);
    doc.text("Meal Plan", 105, y, { align: "center" });

    y += 10;

    const columns = ["", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"];
    const rows = [
      ["Breakfast", ...mealplan.dailyMealPlans.map(dayPlan => dayPlan.breakfast)],
      ["AM Snack", ...mealplan.dailyMealPlans.map(dayPlan => dayPlan.amSnack)],
      ["Lunch", ...mealplan.dailyMealPlans.map(dayPlan => dayPlan.lunch)],
      ["Dinner", ...mealplan.dailyMealPlans.map(dayPlan => dayPlan.dinner)],
      ["PM Snack", ...mealplan.dailyMealPlans.map(dayPlan => dayPlan.pmSnack)]
    ];

    doc.autoTable({
      startY: y,
      head: [columns],
      body: rows
    });

    const pdfURL = doc.output("bloburl");
    const link = document.createElement("a");
    link.href = pdfURL;
    link.download = "mealplan.pdf";
    
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  return (
    <div className="App">
      {isLoading || mealplan === null ? <LoadingSpinner /> : (
    <div id="nutrition-page-div" className="page-div">
      <div className="nutrition-banner"> 
        <p className="mp-title">Your weekly meal plan</p>
        <img src="/images/icons/food.png" alt=""></img>
      </div>
      <hr className="content-divider"></hr>
      <div id="mealplan-bg">
        <MealPlan mealplan={mealplan.dailyMealPlans}/>
        <img src="/images/download.png" alt="Download as PDF" id="download-link" onClick={() => downloadMealplan()}></img>
      </div>
    </div>
  )}
    </div>
  );
}

export default Nutrition;
