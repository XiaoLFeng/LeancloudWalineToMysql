package com.xlf.lwtm

fun main() {
    println("========================================")
    println("     Waline LeanCloud Converter")
    println("         LeanCloud To Mysql")
    println("           筱锋xiao_lfeng")
    println("              V1.0.0")
    println("========================================")
    print("\n\n")
    println("[INFO] 请将 Comment.0.jsonl、Counter.0.jsonl、Users.0.jsonl 文件放置在本程序同目录下")
    println("[INFO] 确认是否进行转换（转换过程中请勿关闭程序）")
    print("\t（1.确认; 2.取消）：")
    // 判断是否开始
    val input = readlnOrNull()
    if (input != null && input == "1") {
        // 文件检查
        if (FileCheck.check()) {
            println("[SUCCESS] 文件检查成功")
        } else {
            return
        }
        // 文件校验
        if (FileCheck.verify()) {
            println("[SUCCESS] 文件校验成功")
        } else {
            return
        }

        // 数据库连接检查
        val database = Database()
        database.checkConnect()
        if (database.insertUsers()) {
            println("[SUCCESS] 用户数据导入成功")
        } else {
            return
        }
        if (database.insertComment()) {
            println("[SUCCESS] 评论数据导入成功")
        } else {
            return
        }
    }
    return
}