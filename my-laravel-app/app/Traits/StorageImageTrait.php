<?php

namespace App\Traits;


use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Str;

trait StorageImageTrait
{
    function storageTraitUpload(Request $request, $fieldName, $folderName)
    {
        if ($request->hasFile($fieldName)) {
            $file = $request->$fieldName;

            $fileNameOrigin = $file->getClientOriginalName();

            $fileNameHash = Str::random(20) . '.' . $file->getClientOriginalExtension();

            $filePath = $request->file($fieldName)->storeAs('public/' . $folderName . '/' . Auth::id(), $fileNameHash);

            $dataUploadTrait = [
                'filename' => $fileNameOrigin,
                'filepath' => Storage::url($filePath)
            ];
            return $dataUploadTrait;
        }
        return null;
    }


    function storageTraitUploadMutiple($file, $folderName)
    {



        $fileNameOrigin = $file->getClientOriginalName();

        $fileNameHash = Str::random(20) . '.' . $file->getClientOriginalExtension();

        $filePath = $file->storeAs('public/' . $folderName . '/' . Auth::id(), $fileNameHash);

        $dataUploadTrait = [
            'filename' => $fileNameOrigin,
            'filepath' => Storage::url($filePath)
        ];
        return $dataUploadTrait;
    }
}
