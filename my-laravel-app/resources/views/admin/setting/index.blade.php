@extends('layouts.admin')


@section('title')
    <title>Settings</title>
@endsection
@section('css')
    <link rel="stylesheet" href="{{ asset('admins/setting/index/index.css') }}">
@endsection
@section('js')
    <script src=" {{ asset('vendors/sweetAlert2/sweetalert2@11.js') }}"></script>
    <script src="{{ asset('admins/main.js') }}"></script>
@endsection
@section('content')
    <div class="content-wrapper">
        @include('partials.content-header', ['name' => 'Menus', 'key' => 'List'])
        <div class="content-header">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">

                        <div class="btn-group float-right">
                            <button type="button" class="btn btn-success dropdown-toggle btn-edit" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                                Action
                            </button>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="{{ route('settings.create', ['type' => 'text']) }}">Text</a>
                                <a class="dropdown-item"
                                    href="{{ route('settings.create', ['type' => 'textarea']) }}">Textarea</a>

                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Separated link</a>
                            </div>
                        </div>


                    </div>
                    <div class="col-md-12">
                        <div class="row mb-2">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">Config Key</th>
                                        <th scope="col">Config value</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    @foreach ($settings as $settingItem)
                                        <tr>
                                            <th scope="row">{{ $settingItem->id }}</th>
                                            <td>{{ $settingItem->config_key }}</td>
                                            <td>{{ $settingItem->config_value }}</td>
                                            <td>
                                                <a href="{{ route('settings.edit', ['id' => $settingItem->id]) }}"
                                                    class="btn btn-default">Edit</a>
                                                <a data-url="{{ route('settings.delete', ['id' => $settingItem->id]) }}"
                                                    class="btn btn-danger action_delete">Delete</a>
                                            </td>
                                        </tr>
                                    @endforeach
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-md-12">
                        {{ $settings->links() }}
                    </div>
                </div>
                <!-- /.row -->
            </div><!-- /.container-fluid -->
        </div>

        {{-- <div class="container-fluid">
            <div class="row">
                <div class="col-lg-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Card title</h5>
                            <p class="card-text">
                                Some quick example text to build on the card title and make up the bulk of the card's
                                content.
                            </p>
                            <a href="#" class="card-link">Card link</a>
                            <a href="#" class="card-link">Another link</a>
                        </div>
                    </div>

                    <div class="card card-primary card-outline">
                        <div class="card-body">
                            <h5 class="card-title">Card title</h5>
                            <p class="card-text">
                                Some quick example text to build on the card title and make up the bulk of the card's
                                content.
                            </p>
                            <a href="#" class="card-link">Card link</a>
                            <a href="#" class="card-link">Another link</a>
                        </div>
                    </div><!-- /.card -->
                </div>
                <!-- /.col-lg-6 -->

                <div class="col-lg-6">
                    <div class="card">
                        <div class="card-header">
                            <h5 class="m-0">Featured</h5>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title">Special title treatment</h6>
                            <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                            <a href="#" class="btn btn-primary">Go somewhere</a>
                        </div>
                    </div>

                    <div class="card card-primary card-outline">
                        <div class="card-header">
                            <h5 class="m-0">Featured</h5>
                        </div>
                        <div class="card-body">
                            <h6 class="card-title">Special title treatment</h6>
                            <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                            <a href="#" class="btn btn-primary">Go somewhere</a>
                        </div>
                    </div>
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
        </div><!-- /.container-fluid --> --}}
    </div>
@endsection
