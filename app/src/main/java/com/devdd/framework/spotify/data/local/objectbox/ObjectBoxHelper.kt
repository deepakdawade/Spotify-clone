package com.devdd.framework.spotify.data.local.objectbox

import com.devdd.framework.spotify.data.local.objectbox.ObjectBox.boxStore

/**
 * Created by @author Deepak Dawade on 9/20/2020 at 4:41 PM.
 * Copyright (c) 2020 deepakdawade.dd@gmail.com All rights reserved.
 **/
class ObjectBoxHelper {

    companion object {

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                      General Extensions and functions
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        fun activeMemberDetails() =
//            BoxFamilyDetailData.selfOrFirstMember() ?: FamilyMember().apply {
//                name = "Guest"
//                nlName = "अतिथि"
//                id = -1
//            }

//        fun selfOrFirstMember() = BoxFamilyDetailData.selfOrFirstMember() ?: FamilyMember().apply {
//            name = "Guest"
//            nlName = "अतिथि"
//            id = -1
//        }

        fun <T> getTableData(table: Class<T>): List<Class<T>>? {
            return ObjectBoxHelper.isEmpty(
                boxStore.boxFor(table::class.java).all
            )
        }

        fun <T> isTableEmpty(table: Class<T>): Boolean {
            return boxStore.boxFor(table).isEmpty
        }

        fun <T> removeEntry(table: Class<T>, obId: Long): Boolean {
            return boxStore.boxFor(table).remove(obId)
        }

        fun <T> removeEntity(table: Class<T>) {
            return boxStore.boxFor(table).removeAll()
        }

        fun <T> isEmpty(list: List<T>?): List<T>? {
            return when {
                list.isNullOrEmpty() -> null
                else -> list
            }
        }

        val listOfEntities = boxStore.allEntityClasses
    }

    object OnDataDirty {
        fun removeAll() {
            boxStore.removeAllObjects()
        }
    }

    object DeleteEntities {
        fun onLogOutUser() {
//            val boxAayuCardSubscription: Box<AayuCardSubscription> = boxStore.boxFor()
//            boxFamilyDetailData.removeAll()
        }
    }
}
