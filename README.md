# [AndroidTrainingPlan](https://17media.atlassian.net/wiki/spaces/InStream/pages/706478430/Android+Training+Plan)

要求
---
1. 請使用MVVM架構及coroutine進行專案開發

2. 將上述Api資料所提供的Github user資料寫入資料庫

3. 將DB中的使用者資料，使用List呈現

4. (option) 可對list item進行刪除，修改，並將資料更新於DB中

標準
---
1. 專案使用MVVM架構設計

2. 背景執行讀取資料及前景顯示資料，是否有使用coroutine來進行前後景執行的切換

3. 是否有針對沒有網路的狀態下做Error Handling

4. 寫入資料庫是否有使用Room套件

5. Call API及使用Room是否有使用repository pattern

List API: 
---
https://docs.github.com/en/rest/reference/users#list-users 

 
