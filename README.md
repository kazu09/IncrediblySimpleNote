# IncrediblySimpleNote
## アプリ紹介
基本機能のみを実装したメモアプリになります。
* 選択画面
  * メモを保存するごとにアイテムが増えていきます
  * ゴミ箱アイコンをタップするとデータが削除されます。
  * アイテムをタップすると編集することができます。
  * アイコンをタップすると新規メモ入力画面へ遷移します。
<img alt="select画面" width="200" src="https://github.com/kazu09/IncrediblySimpleNote/assets/64839248/4e4ea2a8-910d-4c8c-a41f-1731d7fe8a88">

* メモ入力画面
  * タイトル未入力の場合は「No Title」で保存されます。
  * バックキーまたは戻るアイコンをタップすると自動保存されます。
<img alt="memo画面" width="200" src="https://github.com/kazu09/IncrediblySimpleNote/assets/64839248/50824f6a-9aaa-4ec8-9160-99be7689d11f">

## DB
|Column|Type|説明|
|------|----|---|
|uid|int|id|
|title|String|タイトル|
|content|String|メモ内容|

---
## App Introduction
This is a note-taking app with only the basic features implemented.

* Selection Screen
  * As you save notes, the list of items will grow.
  * Tapping the trash can icon will delete the data.
  * Tapping an item allows you to edit it.
  * Tapping the icon takes you to the new note input screen.
<img alt="select画面" width="200" src="https://github.com/kazu09/IncrediblySimpleNote/assets/64839248/4e4ea2a8-910d-4c8c-a41f-1731d7fe8a88">

* Note Input Screen
  * If no title is entered, it will be saved as "No Title".
  * Tapping the back key or the back icon will automatically save the note.
<img alt="memo画面" width="200" src="https://github.com/kazu09/IncrediblySimpleNote/assets/64839248/50824f6a-9aaa-4ec8-9160-99be7689d11f">


## Database
|Column|Type|Description|
|------|----|---|
|uid|int|id|
|title|String|title|
|content|String|Note content|
